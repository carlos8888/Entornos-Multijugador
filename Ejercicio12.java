package ejercicios;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class Ejercicio12 {

	static volatile int val;
	static volatile boolean enCliente;
	static volatile boolean enProxy;
	static volatile boolean enServidor;
	static volatile int n;
	static final int SIZE = 10;

	public static void servidor() {

		while (n < SIZE) {
			while (!enServidor)
				;
			val++;
			enServidor = false;
		}

	}

	public static void proxy() {

		while (n < SIZE) {
			while (!enProxy)
				;
			val++;
			enServidor = true;
			while (enServidor)
				;
			enProxy = false;
		}

	}

	public static void cliente() {

		while (n < SIZE) {
			if (n > 0)
				print("- - - - -\n");
			print("Enviando: " + val + "\n");
			enProxy = true;
			while (enProxy)
				;
			print("Recibido: " + val + "\n");
			n++;
		}

	}

	public static void main(String[] args) {
		n = 0;
		val = 0;
		enCliente = true;
		enProxy = false;
		enServidor = false;
		createThread("cliente");
		createThread("proxy");
		createThread("servidor");
		startThreadsAndWait();
	}

}
