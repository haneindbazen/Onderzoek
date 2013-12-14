package com.simulatieTool.Webapp;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;


public class Drools {
	
	static KnowledgeBase kbase;
	static StatefulKnowledgeSession ksession;
	static KnowledgeRuntimeLogger logger;
	
	public static void Initialize() throws Exception{
		kbase = readKnowledgeBase();
        ksession = kbase.newStatefulKnowledgeSession();
        logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
	}
	
	public static void FireRules(Event event){
		try {
			ksession.insert(event);
            ksession.fireAllRules();
           //======================//
            logger.close();
        Thread.sleep(2000);
		} catch (Throwable t) {
            t.printStackTrace();
        }
	}
	 public static KnowledgeBase readKnowledgeBase() throws Exception {
	        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	        kbuilder.add(ResourceFactory.newClassPathResource("Sample.drl"), ResourceType.DRL);
	        KnowledgeBuilderErrors errors = kbuilder.getErrors();
	        if (errors.size() > 0) {
	            for (KnowledgeBuilderError error: errors) {
	                System.err.println(error);
	            }
	            throw new IllegalArgumentException("Could not parse knowledge.");
	        }
	        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	        return kbase;
	    }
	 
	 public static String setText(){
		 String a = null;
		 for(int i=0 ;i<3; i++){
			 a = "test";
		 }
		return a;
	 }
	 
}
