package com.erezbiox1;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageSentEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;

public class Main{

    public static void main(String[] args) {

        if(args == null || args.length != 2){
            System.err.println("Invalid Arguments");
            return;
        }

        Skype skype = new SkypeBuilder(args[0], args[1]).withAllResources().build();

        try {

            System.out.println("Loggin in...");
            skype.login();
            skype.subscribe();


        } catch (InvalidCredentialsException | NotParticipatingException e) {
            System.err.println("Bad login.");
        } catch (ConnectionException e) {
            System.err.println("No connection to the server.");
        }

        System.out.println("Done!");
        skype.getEventDispatcher().registerListener(new Listener() {

            @EventHandler
            public void onMessage(MessageSentEvent event){
                if(!(event.getMessage().getContent().asPlaintext().equalsIgnoreCase("!getChatID"))) return;

                System.out.println("Message received, ChatID:");
                System.out.println(event.getChat().getIdentity());

                try {
                    event.getChat().sendMessage(event.getChat().getIdentity());
                } catch (ConnectionException e) {
                    System.err.println("No connection to the server.");
                }
            }

        });
    }


}
