package sptech.school;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CapturaRede extends configuracao{
    private Integer fkConfig;
    private String hostTeste ;
    private Integer porta ;
    private static final Integer metrica_latencia = 5; // -> Latência
    private static final Integer metrica_conectividade = 6;  // -> Status Conectividade
    private static final Integer metrica_rede = 7;
    public String getHostTeste() {
        return hostTeste;
    }
    
    public void setHostTeste(String hostTeste) {
        this.hostTeste = hostTeste;
    }
    
    public Integer getPorta() {
        return porta;
    }
    
    public void setPorta(Integer porta) {
        this.porta = porta;
    }
    
    public CapturaRede(Integer config){
        super();
        this.fkConfig = config;
        this.hostTeste = "1.1.1.1"; // <- dns publico só pra teste
        this.porta = 3306  ; // <- porta padrão para tráfego http
    }
    
    @Override
    public void executarCaptura() {
        long tempoInicio = System.nanoTime();
        boolean conexao = testarConexao(this.hostTeste, this.porta, 3000);
        long tempoFinal = System.nanoTime();
        
        double latencia;
        double conectividade;
        double statusRede;
        
        if (conexao) {
            latencia = (tempoFinal - tempoInicio) / 1_000_000.0; // <- converte nano segundos para milisegundos
            conectividade = 1.0;
            statusRede = 1.0;
            System.out.print("ONLINE");
            System.out.println("\n");
            System.out.println(latencia);
        } else {
            latencia = 0.0;
            conectividade = 0.0;
            statusRede = 0.0;
            System.out.println("OFFLINE");
        }
        
        java.sql.Timestamp dataHoraAtual = java.sql.Timestamp.valueOf(LocalDateTime.now());
        
        
        String sql = "INSERT INTO registro_monitoramento (fk_config, fk_metrica, valor, dt_hora) VALUES (?, ?, ?, ?)";


        new configuracao(sql, this.fkConfig, metrica_latencia, latencia,dataHoraAtual) {
            @Override
            public void executarCaptura() {
            
            }
        };
        new configuracao(sql, this.fkConfig, metrica_conectividade, conectividade, dataHoraAtual) {
            @Override
            public void executarCaptura() {
            
            }
        };
        new configuracao(sql, this.fkConfig, metrica_rede, statusRede,dataHoraAtual) {
            @Override
            public void executarCaptura() {
            }
        
        
        };
    }
    
    private boolean testarConexao(String host, Integer porta, Integer timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, porta), timeout);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    }

