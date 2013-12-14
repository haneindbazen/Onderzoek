package com.simulatieTool.Webapp;

import java.util.ArrayList;
import java.util.List;

interface IListener {
    public void Listen(String message);
}


class Initiater {
    List<IListener> listeners = new ArrayList<IListener>();

    public void addListener(IListener toAdd) {
        listeners.add(toAdd);
}

    public void sayHello() throws InterruptedException {
    
    	
    	for(int i=0;i<10;i++){
        System.out.println("Hello!!");

        // Notify everybody that may be interested.
        for (IListener hl : listeners){
            hl.Listen("Hello!!");
        }
    }
    }
}


class Listener implements IListener {
    @Override
    public void Listen(String message) {
        System.out.println("Someone said:" + message);
    }
}

public class EventTest {
    public static void main(String[] args) throws InterruptedException {
        Initiater initiater = new Initiater();
        Listener listener = new Listener();

        initiater.addListener(listener);

        initiater.sayHello();
    }
}