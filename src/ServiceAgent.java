package jadelab1;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;
import java.net.*;
import java.io.*;

public class ServiceAgent extends Agent {
	protected void setup () {
		// TODO: [ZAD3] <Step2>: Add only one service (all_dict_service)
		//services registration at DF
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.setName(getAID());
		//service no 1
		ServiceDescription sd1 = new ServiceDescription();
		sd1.setType("all_dict_service");
		sd1.setName("all_dict_service");
		dfad.addServices(sd1);

		try {
			DFService.register(this,dfad);
		} catch (FIPAException ex) {
			ex.printStackTrace();
		}

		// TODO: [ZAD3] <Step3>: Add only one Behaviour
		addBehaviour(new AllDictCyclicBehaviour(this));
		//doDelete();
	}
	protected void takeDown() {
		//services deregistration before termination
		try {
			DFService.deregister(this);
		} catch (FIPAException ex) {
			ex.printStackTrace();
		}
	}
	public String makeRequest(String serviceName, String word)
	{
		StringBuffer response = new StringBuffer();
		try
		{
			URL url;
			URLConnection urlConn;
			DataOutputStream printout;
			DataInputStream input;
			url = new URL("http://dict.org/bin/Dict");
			urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			String content = "Form=Dict1&Strategy=*&Database=" + URLEncoder.encode(serviceName) + "&Query=" + URLEncoder.encode(word) + "&submit=Submit+query";
			//forth
			printout = new DataOutputStream(urlConn.getOutputStream());
			printout.writeBytes(content);
			printout.flush();
			printout.close();
			//back
			input = new DataInputStream(urlConn.getInputStream());
			String str;
			while (null != ((str = input.readLine())))
			{
				response.append(str);
			}
			input.close();
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//cut what is unnecessary
		return response.substring(response.indexOf("<hr>")+4, response.lastIndexOf("<hr>"));
	}
}

// TODO: [ZAD3] <Step4>: Add Bechaviour class named AllDictCycleBehaviour
class AllDictCyclicBehaviour extends CyclicBehaviour
{
	ServiceAgent agent;
	public AllDictCyclicBehaviour(ServiceAgent agent)
	{
		this.agent = agent;
	}
	public void action()
	{
		// TODO: [ZAD3] <Step5>: Remove mathcing by Ontology
		// MessageTemplate template = MessageTemplate.MatchOntology("wordnet");
		ACLMessage message = agent.receive(); //TODO: removed (template) from args
		if (message == null)
		{
			block();
		}
		else
		{
			//process the incoming message
			String content = message.getContent();
			// TODO: [ZAD3] <Step6>: Get Ontology from message
			String dictName = message.getOntology();
			ACLMessage reply = message.createReply();
			reply.setPerformative(ACLMessage.INFORM);
			String response = "";
			try
			{
				// TODO: [ZAD3] <Step7>: Send request with dictName
				response = agent.makeRequest(dictName,content);
			}
			catch (NumberFormatException ex)
			{
				response = ex.getMessage();
			}
			reply.setContent(response);
			agent.send(reply);
		}
	}
}