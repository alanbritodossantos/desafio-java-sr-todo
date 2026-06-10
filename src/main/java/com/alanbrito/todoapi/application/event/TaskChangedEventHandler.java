package com.alanbrito.todoapi.application.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TaskChangedEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(TaskChangedEventHandler.class);

    @EventListener
    public void handle(TaskChangedEvent event) {
        logger.info(
                "Task event processed: action={}, taskId={}, title={}, status={}, occurredAt={}",
                event.action(),
                event.taskId(),
                event.title(),
                event.status(),
                event.occurredAt()
        );
    }
}