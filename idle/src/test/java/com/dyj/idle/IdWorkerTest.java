package com.dyj.idle;

import com.dyj.idle.utils.IdWorker;
import org.junit.jupiter.api.Test;

public class IdWorkerTest {

    @Test
    void test(){
        IdWorker idWorker=new IdWorker(0,0);
        System.out.println(idWorker.nextId());

    }
}
