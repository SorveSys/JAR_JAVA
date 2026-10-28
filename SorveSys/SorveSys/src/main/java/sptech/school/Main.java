package sptech.school;

public class Main {
    static void main(String[] args) {
        
        CapturaIP capturaServidor = new CapturaIP("transito", 1);
        CapturaRede Rede = new CapturaRede(4);
        
        int intervaloSegundos = 5000;
        
        while (true) {
            try {
                System.out.println("\n");
                
                capturaServidor.executarCaptura();
                Rede.executarCaptura();
                
                Thread.sleep(intervaloSegundos);
                
            } catch (InterruptedException e) {
                System.err.println("Loop interrompido: " + e.getMessage());
                break;
            } catch (Exception e) {
                System.err.println("Erro na captura: " + e.getMessage());
            }
        }
    }
}


