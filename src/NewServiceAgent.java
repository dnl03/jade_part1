// TODO: [ZAD2] Add new java file File for better reading

package jadelab1;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;
import java.net.*;
import java.io.*;

//TODO: [ZAD2] Add new agent and add it to the DF
public class NewServiceAgent extends Agent {
    protected void setup () {
        //services registration at DF
        DFAgentDescription dfad = new DFAgentDescription();
        dfad.setName(getAID());
        //service no 1
        ServiceDescription sd1 = new ServiceDescription();
        sd1.setType("answers");
        sd1.setName("new_agent_dict");

        //add them all
        dfad.addServices(sd1);

        try {
            DFService.register(this,dfad);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }

        addBehaviour(new NewAgentDictonaryCyclicBehaviour(this));

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

//TODO: [ZAD2] Add New Agent Dict Bechaviour class. Remember to change type of arg Agent(NewServiceAgent) in constructor
class NewAgentDictonaryCyclicBehaviour extends CyclicBehaviour
{
    NewServiceAgent agent;
    public NewAgentDictonaryCyclicBehaviour(NewServiceAgent agent)
    {
        this.agent = agent;
    }
    public void action()
    {
        //TODO: [ZAD2] Ontology field name (the same in NewAgent : sd1.setName("new_agent_dict");)
        MessageTemplate template = MessageTemplate.MatchOntology("new_agent_dict");
        ACLMessage message = agent.receive(template);
        if (message == null)
        {
            block();
        }
        else
        {
            //process the incoming message
            String content = message.getContent();
            ACLMessage reply = message.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            String response = "";
            try
            {
                //TODO: [ZAD2] option_value from dict on dict website
                response = agent.makeRequest("fd-pol-deu", content);
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