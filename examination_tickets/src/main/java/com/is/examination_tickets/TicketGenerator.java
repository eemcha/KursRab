package com.is.examination_tickets;

import java.util.ArrayList;
import java.util.Collections;

public class TicketGenerator {
	static ArrayList<ArrayList<String>> generator(ArrayList<String> questions, int numberOfTickets,
			int questionsInTickets) {
		ArrayList<ArrayList<String>> Ticket = new ArrayList<ArrayList<String>>();
		if (!check(numberOfTickets, questions.size(), questionsInTickets)) {
			ArrayList<String> tmp = new ArrayList<String>();
			tmp.add("Слишком мало вопросов для такого колличества билетов");
			Ticket.add(tmp);
			return null;
		}
		for (int i = 0; i < numberOfTickets; i++) {
			boo(questions, questionsInTickets, Ticket);
		}
		return Ticket;
	}

	private static void boo(ArrayList<String> questions, int questionsInTickets, ArrayList<ArrayList<String>> ticket) {
		ArrayList<String> tmp = new ArrayList<String>();
		for (int j = 0; j < questionsInTickets; j++) {
			String s = foo(tmp, questions);
			tmp.add(s);
		}
		Collections.sort(tmp);
		if ((ticket != null) && (ticket.contains(tmp)))
			boo(questions, questionsInTickets, ticket);
		else
			ticket.add(tmp);
	}

	private static String foo(ArrayList<String> tmp, ArrayList<String> questions) {
		int size = questions.size();
		int num = (int) (Math.random() * size);
		String ret = questions.get(num);
		if ((tmp != null) && (tmp.contains(ret))) {
			ret = foo(tmp, questions);
		}
		return ret;
	}

	static boolean check(int numberOfTickets, int questions, int questionsInTickets) {
		int possibleNumberOfTickets;
		possibleNumberOfTickets = factorial(questions)
				/ (factorial(questionsInTickets) * factorial(questions - questionsInTickets));
		return (possibleNumberOfTickets >= numberOfTickets);
	}

	private static int factorial(int n) {
		if (n == 0)
			return 1;
		return n * factorial(n - 1);
	}
}
