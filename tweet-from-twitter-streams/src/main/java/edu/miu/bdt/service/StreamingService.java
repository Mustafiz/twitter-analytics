package edu.miu.bdt.service;

import edu.miu.bdt.listener.TweeterStreamListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mustafizur Rahman Hilaly
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StreamingService {

    private final Twitter twitter;
    private final TweeterStreamListener streamListener;

    public void run() {
        List<StreamListener> listeners = new ArrayList<>();

        listeners.add(streamListener);
        twitter.streamingOperations().sample(listeners);
    }
}
