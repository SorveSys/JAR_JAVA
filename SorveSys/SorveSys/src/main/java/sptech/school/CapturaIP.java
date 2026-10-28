package sptech.school;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import java.util.List;

public class CapturaIP extends configuracao {

    private String tipoServidor;
    private Integer empresa;

    // CONSTRUTOR DE 2 PARÂMETROS (Resolve o erro na Main.java)
    public CapturaIP(String tipoServidor, Integer empresa) {
        super(new Looca());
        this.tipoServidor = tipoServidor;
        this.empresa = empresa;
    }

    // CONSTRUTOR DE 3 PARÂMETROS
    public CapturaIP(Looca looca, String tipoServidor, Integer empresa) {
        super(looca);
        this.tipoServidor = tipoServidor;
        this.empresa = empresa;
    }

    @Override
    public void executarCaptura() {
        // 1. Tenta pegar o IP real da máquina repassado pelo Docker
        String ipv4 = System.getenv("HOST_IP");

        // 2. Se não houver variável de ambiente, usa a Looca
        if (ipv4 == null || ipv4.isBlank()) {
            List<RedeInterface> interfaces = looca.getRede().getGrupoDeInterfaces().getInterfaces();
            RedeInterface placaAtiva = interfaces.stream()
                    .filter(i -> i.getEnderecoIpv4() != null && !i.getEnderecoIpv4().isEmpty())
                    .findFirst()
                    .orElse(null);

            if (placaAtiva != null) {
                ipv4 = placaAtiva.getEnderecoIpv4().get(0);
            }
        }

        if (ipv4 != null && !ipv4.isBlank()) {
            System.out.println("IP Identificado para monitoramento: " + ipv4);

            // 3. Consulta se o servidor já existe no banco
            String sqlSelect = "SELECT id FROM servidor WHERE ip = ?";

            // Execute a consulta usando a sua classe de conexão base (ex: jdbcTemplate ou conexão manual)
            // Integer idServidor = executarConsulta(sqlSelect, ipv4);

            /*
               LÓGICA DE NEGÓCIO:
               - Se idServidor != null: Utiliza o idServidor para gravar os dados de REDE na tabela 'registro_monitoramento'.
               - Se idServidor == null: Exibe o aviso informando que a máquina precisa ser cadastrada pelo Gestor.
            */
        } else {
            System.out.println("Não foi possível identificar o IP da máquina.");
        }
    }

    // Getters e Setters
    public String getTipoServidor() { return tipoServidor; }
    public void setTipoServidor(String tipoServidor) { this.tipoServidor = tipoServidor; }
    public Integer getEmpresa() { return empresa; }
    public void setEmpresa(Integer empresa) { this.empresa = empresa; }
}