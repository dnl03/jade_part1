package jadelab1;

//TODO: [ZAD4], <step1> Import HashMap
import java.util.HashMap;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyAgent extends Agent {
	//TODO: [ZAD4], <step1> Define HashMap for storage sent messages
	private HashMap<String, String> sent_messages = new HashMap<>();
	protected void setup () {
		displayResponse("Hello, I am " + getAID().getLocalName());
		addBehaviour(new MyCyclicBehaviour(this));
		//doDelete();
	}
	protected void takeDown() {
		displayResponse("See you");
	}
	public void displayResponse(String message) {
		JOptionPane.showMessageDialog(null,message,"Message",JOptionPane.PLAIN_MESSAGE);
	}
	public void displayHtmlResponse(String html) {
		JTextPane tp = new JTextPane();
		JScrollPane js = new JScrollPane();
		js.getViewport().add(tp);
		JFrame jf = new JFrame();
		jf.getContentPane().add(js);
		jf.pack();
		jf.setSize(400,500);
		jf.setVisible(true);
		tp.setContentType("text/html");
		tp.setEditable(false);
		tp.setText(html);
	}
	// TODO: [ZAD4] <step2> Add setter and getter to private hash map
	public void add_to_hashmap(String id, String content)
	{
		this.sent_messages.put(id, content);
	}
	public String get_from_hashmap(String id)
	{
		return this.sent_messages.get(id);
	}
}

class MyCyclicBehaviour extends CyclicBehaviour {
	MyAgent myAgent;
	public MyCyclicBehaviour(MyAgent myAgent) {
		this.myAgent = myAgent;
	}
	public void action() {
		ACLMessage message = myAgent.receive();
		if (message == null) {
			block();
		} else {
			String ontology = message.getOntology();
			String content = message.getContent();
			int performative = message.getPerformative();
			if (performative == ACLMessage.REQUEST)
			{
				//I cannot answer but I will search for someone who can
				DFAgentDescription dfad = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setName(ontology);
				dfad.addServices(sd);
				try
				{
					DFAgentDescription[] result = DFService.search(myAgent, dfad);
					if (result.length == 0) myAgent.displayResponse("No service has been found ...");
					else
					{
						// TODO: [ZAD4] <step3> Create Unique ID
						String msg_id = String.valueOf(System.currentTimeMillis());

						String foundAgent = result[0].getName().getLocalName();
						myAgent.displayResponse("Agent " + foundAgent + " is a service provider. Sending message to " + foundAgent);
						ACLMessage forward = new ACLMessage(ACLMessage.REQUEST);
						forward.addReceiver(new AID(foundAgent, AID.ISLOCALNAME));


						// TODO: [ZAD4] <step4> Set unique_id on attr 'replay-with'
						forward.setReplyWith(msg_id);

						forward.setContent(content);
						forward.setOntology(ontology);

						// TODO [ZAD4] <step5> Add msg_id and word to hash map
						myAgent.add_to_hashmap(msg_id, content);
						myAgent.send(forward);
					}
				}
				catch (FIPAException ex)
				{
					ex.printStackTrace();
					myAgent.displayResponse("Problem occured while searching for a service ...");
				}
			}
			else
			{	//when it is an answer
				//TODO: [ZAD4] Receiving Messages, and recognize word by unique ID
				String replay_with = message.getInReplyTo();
				String sent_word = myAgent.get_from_hashmap(replay_with);
				if (sent_word != null) {
					myAgent.displayHtmlResponse("<h1>ID-Request: " + replay_with + "</h1><h3>Send: " + sent_word + "</h3> <h3>Received: <h3><hr>" + content);
				}
				else {
					myAgent.displayResponse("Unknow msg id: " + replay_with);
				}
			}
		}
	}
}
