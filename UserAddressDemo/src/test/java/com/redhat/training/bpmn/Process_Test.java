package com.redhat.training.bpmn;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.test.JbpmJUnitTestCase;
import org.junit.Test;

public class Process_Test extends JbpmJUnitTestCase {

	@Test
	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("EnterCustomerInformation.bpmn");
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", new TestWorkItemHandler());
		ProcessInstance p = ksession.startProcess("UserAddressDemo", null);
		System.out.println(p.getProcessId()+p.getProcessName());
		assertProcessInstanceActive(p.getId(), ksession);
	}

}
