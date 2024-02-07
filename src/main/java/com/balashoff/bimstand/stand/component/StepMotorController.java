package com.balashoff.bimstand.stand.component;

import com.balashoff.bimstand.components.StepMotorComponent;
import com.balashoff.bimstand.model.message.curtains.CurtainsAction;
import com.balashoff.bimstand.model.message.curtains.CurtainsStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Log4j2
public class StepMotorController implements Runnable {
    private final StepMotorComponent stepMotorComponent;
    private final BlockingQueue<CurtainsAction> queueOfStatus = new ArrayBlockingQueue<>(100);
    private Consumer<CurtainsStatus> consumeStatus;


    public void addAction(CurtainsAction action) {
       boolean isAdd = queueOfStatus.add(action);
        log.debug("add action: {} added: {}", action, isAdd);
    }

    @Override
    public void run() {
        do {
            try {
                CurtainsAction action = queueOfStatus.take();
                log.debug("--->Incoming action: {}", action);
                CurtainsStatus statusAfterAction = makeAction(action);
                if(!statusAfterAction.equals(CurtainsStatus.undef)){
                    consumeStatus.accept(statusAfterAction);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }while (true);
    }

    private CurtainsStatus makeAction(CurtainsAction action) {
        CurtainsStatus afterAction = CurtainsStatus.undef;
        switch (action){
            case forward -> {
                int pos = stepMotorComponent.manualForward();
                if(pos == -1){
                    afterAction = CurtainsStatus.closed;
                }else if(pos == 1){
                    afterAction = CurtainsStatus.opened;
                }else{
                    afterAction = CurtainsStatus.slightly_open;
                }

            }
            case backward -> {
                int pos = stepMotorComponent.manualBackward();
                if(pos == -1){
                    afterAction = CurtainsStatus.closed;
                }else if(pos == 1){
                    afterAction = CurtainsStatus.opened;
                }else{
                    afterAction = CurtainsStatus.slightly_open;
                }
            }
            case close -> {
                int pos = stepMotorComponent.autoClose();
                afterAction = pos == 0 ? CurtainsStatus.closed : CurtainsStatus.slightly_open;
            }
            case open -> {
                int pos = stepMotorComponent.autoOpen();
                afterAction = pos == 1 ? CurtainsStatus.opened : CurtainsStatus.slightly_open;
            }
        }
        log.debug("<--Output message: {}", afterAction);
        return afterAction;
    }


    public void addControllerHandler(Consumer<CurtainsStatus> consumeStatus) {
        this.consumeStatus = consumeStatus;
    }
}
