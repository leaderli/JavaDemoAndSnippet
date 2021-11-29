package com.leaderli.demo.demo;



import junit.framework.Assert;

import java.util.function.Predicate;

public class BooleanTest {
    
    public static void main(String[] args) {
        Link link = new Link();
        link.predicate = p -> p.length() > 10;
        
        Link or = new Link() {
            @Override
            public boolean cancel(boolean last) {
                if (!last) {
                    next = next.next;
                    Assert.assertNotNull( "or后必须跟着其他操作符",next);
                }
                return last;
            }
        };
        Link link2 = new Link();
        link2.predicate = p -> p.length() == 1;
        link.add(or).add(link2);
        boolean accpet = true;
        while (link != null) {
            if (link.cancel(accpet)) {
                System.out.println(accpet);
                return;
            }
            accpet = link.accpet("123");
            link = link.next;
        }
    }
    
    static int ID = 0;
    
    static class Link {
        public Link prev;
        public Link next;
        public Predicate<String> predicate;
        
        public boolean cancel(boolean last) {
            return false;
        }
        
        public boolean accpet(String test) {
            return predicate.test(test);
        }
        
        
        public Link add(Link link) {
            this.next = link;
            link.prev = this;
            return link;
        }
        
    }
}
    

