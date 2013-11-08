package server;

import java.util.List;

import server.Sensors.OceanConnexionManager;
import server.database.service.OperatorService;
import server.models.Action;
import server.models.ActionOperator;
import server.models.Operator;

public class ThreadOperators extends Thread {
	final private static int UPDATE_TIME = 1000;


	public ThreadOperators() {

	}

	public void run() {

		OperatorService se = new OperatorService();
		for (;;) {


			try {
				Thread.sleep(UPDATE_TIME);
				if (OceanConnexionManager.getConnexionisActive())
				{
				List<ActionOperator> l = se.getActionTodo();
				for (ActionOperator actionOperator : l) {
					Action a = actionOperator.getAction();
					Operator o = actionOperator.getOperator();
					// TODO: d�placer le code ci-dessous dans une m�thode execute dans la classe operator
					o.execute(a);

				}
				}
				else
				{
					System.out.println("Connexion is not active, action will be done when it will be ok !");
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}

