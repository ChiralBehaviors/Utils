/** (C) Copyright 2013 Hal Hildebrand, All Rights Reserved
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.hellblazer.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import com.hellblazer.utils.collections.RingBuffer;

/**
 * @author hhildebrand
 * 
 */
public class RingBufferTest {
    @Test
    public void testAccordian() {
        Random r = new Random(0x666);
        RingBuffer<Integer> test = new RingBuffer<Integer>(1000);
        for (int i = 0; i < 500; i++) {
            test.add(i);
        }
        int count = test.size();
        for (int i = 0; i < 10000; i++) {
            if (r.nextBoolean()) {
                assertTrue(test.offer(i));
                count++;
                assertEquals(count, test.size());
            } else {
                assertNotNull(test.poll());
                count--;
                assertEquals(count, test.size());
            }
        }
        assertEquals(count, test.size());
    }

    @Test
    public void testAt() {
        RingBuffer<Integer> test = new RingBuffer<>(1000);
        for (int i = 0; i < 1000; i++) {
            test.add(i);
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(Integer.valueOf(i), test.at(i));
        }
    }

    @Test
    public void testIterate() {
        RingBuffer<Integer> test = new RingBuffer<>(1000);
        for (int i = 0; i < 1000; i++) {
            test.add(i);
        }
        int i = 0;
        for (Integer value : test) {
            assertEquals(Integer.valueOf(i), value);
            i++;
        }
    }

    @Test
    public void testOffer() {
        RingBuffer<String> test = new RingBuffer<String>(1000);
        for (int i = 0; i < 1000; i++) {
            assertEquals("Invalid size", i, test.size());
            assertTrue(test.offer(String.format("Offer: %s", i)));
        }
        assertEquals("Invalid size", 1000, test.size());
        assertFalse(test.offer(String.format("Offer: %s", 1001)));
    }

    @Test
    public void testPoll() {
        RingBuffer<String> test = new RingBuffer<String>(1000);
        for (int i = 0; i < 1000; i++) {
            test.add(String.format("Add: %s", i));
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals("Invalid size", 1000 - i, test.size());
            assertEquals(String.format("Add: %s", i), test.poll());
        }
        assertNull(test.poll());
    }
}