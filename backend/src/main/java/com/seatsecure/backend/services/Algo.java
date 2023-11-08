package com.seatsecure.backend.services;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.enums.Status;
import com.seatsecure.backend.exceptions.BiddingNotCloseException;

@Component
public class Algo {
    private TicketQueueService queueSer;
    private QueueEntryService entrySer;
    private TicketService ticketSer;

    public Algo(TicketQueueService queueSer, QueueEntryService entrySer, TicketService ticketSer){
        this.queueSer = queueSer;
        this.entrySer = entrySer;
        this.ticketSer = ticketSer;
    }
    public void algoForBidding(Run run){
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime endBidding = run.getEndBidTime();
        if(!run.isAlgoRan() && currentTime.isAfter(endBidding)){

            long delayMillis = Duration.between(currentTime, endBidding).toMillis();

            Runnable task = () -> {
                List<TicketUserQueue> queuesPerRun = queueSer.listAllQueuesPerRun(run);
                Comparator<TicketUserQueue> comparator = new Comparator<TicketUserQueue>() {
                    @Override
                    public int compare(TicketUserQueue obj1, TicketUserQueue obj2) {
                        int result = Integer.compare(obj1.getCat().getPriority(),obj2.getCat().getPriority());
                        return result;
                    }
                };
                Collections.sort(queuesPerRun, comparator);
                int numOfCats = queuesPerRun.size();
                Stack<TicketUserQueue> categoryPerRound = new Stack<>();
                for (int numberOfRounds = 1; numberOfRounds <= numOfCats; numberOfRounds++){
                    categoryPerRound.push(queuesPerRun.get(numberOfRounds));
                    int capacity = queuesPerRun.get(numberOfRounds).getCat().getSeats().size();
                    while (capacity != 0 && !categoryPerRound.isEmpty()){
                        TicketUserQueue currentQueue = categoryPerRound.pop();
                        Long catID = currentQueue.getCat().getId();
                        Long runID = currentQueue.getRun().getId();
                        List<Ticket> forAssignment = eachCattickets(runID, catID);

                        List<QueueEntry> entries = entrySer.listAllQueueEntriesPerQueue(currentQueue);
                        int size = entries.size();
                        SecureRandom randomGen = new SecureRandom();
                        while(capacity != 0){
                            int positionChosen = randomGen.nextInt(size);
                            QueueEntry entryChosen = entries.get(positionChosen);
                            System.out.println("Position: " +  positionChosen + ", Element: " + entryChosen);
                            int numSeatsChosen = entryChosen.getNumOfSeats();
                            if(numSeatsChosen > capacity){
                                numSeatsChosen = capacity;
                                entryChosen.setNumOfSeats(numSeatsChosen - capacity);
                                entryChosen.setStatus(Status.PARTIALLYSUCESSFUL);
                            }   else{
                                QueueEntry toBeReplaced = entries.get(size-1);
                                entries.set(positionChosen, toBeReplaced);
                                entries.set(size-1, entryChosen);
                                size--;
                                entryChosen.setStatus(Status.SUCCESSFUL);
                            }
                            entrySer.updateEntry(entryChosen.getQueueEntryNumber(), entryChosen);
                            ticketAssignment(entryChosen, numSeatsChosen, forAssignment, size-capacity);
                            capacity -= numSeatsChosen;
                            
                        }
                        if (size != 0){
                            List<QueueEntry> adjustedList = new ArrayList<>();
                            for (int index = 0; index < size; index++){
                                adjustedList.add(index, entries.get(index)); 
                            }
                            categoryPerRound.push(queueSer.updateQueuewithUpdatedEntries(adjustedList, currentQueue));
                        }
                    }
                }
                while(!categoryPerRound.isEmpty()){
                    TicketUserQueue queue = categoryPerRound.pop();
                    List<QueueEntry> remainingEntries = entrySer.listAllQueueEntriesPerQueue(queue);
                    statusChange(remainingEntries);
                    System.out.println("Method executed at " + LocalDateTime.now());
                }
            };
            // Use a ScheduledExecutorService to schedule the task
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            executorService.schedule(task, delayMillis, TimeUnit.MILLISECONDS);

            // Shutdown the executor service when done
            executorService.shutdown();
            
        }   else if(currentTime.isBefore(endBidding)){
            throw new BiddingNotCloseException();
        }
    }
    public List<Ticket> eachCattickets(Long runID, Long catID){
        List<Ticket> ticketsPerRun = ticketSer.getTicketsOfRun(runID);
        return ticketsPerCat(ticketsPerRun, catID);
    }

    public List<Ticket> ticketsPerCat(List<Ticket> ticketsPerRun, Long catID){
        List<Ticket> perCat = new ArrayList<>();
        for(Ticket tix : ticketsPerRun){
            if(tix.getSeat().getCat().getId() == catID){
                perCat.add(tix);
            }
        }
        return perCat;
    }

    public void ticketAssignment(QueueEntry entry, int numOfSeatsAllocated, List<Ticket> tickets, int index){
        for(int n = 0; n < numOfSeatsAllocated; n++){
            Long userID = entry.getUser().getId();
            Long tixID = tickets.get(index+n).getId();
            ticketSer.assignTicketToUser(userID, tixID);
            
        }
    }

    public void statusChange(List<QueueEntry> entries){
        int length = entries.size();
        for (int counter = 0; counter < length; counter++){
            QueueEntry entry = entries.get(counter);
            entry.setStatus(Status.UNSUCCESSFUL);
            entrySer.updateEntry(entry.getQueueEntryNumber(),entry);
        }
    }
}
