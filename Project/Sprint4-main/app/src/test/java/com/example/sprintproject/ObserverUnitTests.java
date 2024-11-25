package com.example.sprintproject;

import com.example.sprintproject.model.TravelPost;
import com.example.sprintproject.utils.TravelPostListener;
import com.example.sprintproject.utils.TravelPostObservable;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ObserverUnitTests {
    private TravelPostListener listener1;
    private TravelPostListener listener2;

    private String listener1String = "";
    private String listener2String = "";

    private TestObservable observable1;
    private TestObservable observable2;

    @Before
    public void start() {
        listener1 = (post) -> {
            listener1String += post.getId();
        };
        listener2 = (post) -> {
            listener2String += post.getId();
        };

        observable1 = new TestObservable("NumberOne");
        observable2 = new TestObservable("NumberTwo");
    }

    @Test
    public void subscriptionTests() {
        listener1.subscribe(observable1);
        listener2.subscribe(observable2);

        assertTrue(observable1.isSubscribed(listener1));
        assertTrue(observable2.isSubscribed(listener2));
        assertFalse(observable1.isSubscribed(listener2));

        listener2.unsubscribe(observable2);
        assertTrue(observable1.isSubscribed(listener1));
        assertFalse(observable2.isSubscribed(listener2));
        assertFalse(observable1.isSubscribed(listener2));
    }

    @Test
    public void postTests() {
        listener1.subscribe(observable1);
        listener1.subscribe(observable2);
        listener2.subscribe(observable2);

        observable1.makePost();
        observable2.makePost();

        assertEquals("NumberOne0NumberTwo0", listener1String);
        assertEquals("NumberTwo0", listener2String);

        listener1.unsubscribe(observable2);
        listener2.subscribe(observable1);

        assertEquals("NumberOne0NumberTwo0", listener1String);
        assertEquals("NumberTwo0", listener2String);

        observable1.makePost();
        observable2.makePost();

        assertEquals("NumberOne0NumberTwo0NumberOne1", listener1String);
        assertEquals("NumberTwo0NumberOne1NumberTwo1", listener2String);
    }

    private static class TestObservable implements TravelPostObservable {
        private Set<TravelPostListener> listeners = new HashSet<>();
        private final String id;
        private int count = 0;

        private TestObservable(String id) {
            this.id = id;
        }

        @Override
        public void addListener(TravelPostListener listener) {
            listeners.add(listener);
        }

        @Override
        public void removeListener(TravelPostListener listener) {
            listeners.remove(listener);
        }

        public boolean isSubscribed(TravelPostListener listener) {
            return listeners.contains(listener);
        }

        @Override
        public void updateListeners(TravelPost post) {
            for (TravelPostListener listener : listeners) {
                listener.observePost(post);
            }
        }

        public void makePost() {
            TravelPost post = new TravelPost();
            post.setId(this.id + count++);

            updateListeners(post);
        }
    }
}
