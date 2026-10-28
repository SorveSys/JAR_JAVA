package sptech.school;

import com.github.britooo.looca.api.core.Looca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class configuracao {
    protected Looca looca;
    
    public configuracao() {
        this.looca = new Looca();
    }
    
    public Looca getLooca() {
        return looca;
    }
    
    public void setLooca(Looca looca) {
        this.looca = looca;
    }
    
    public configuracao(Looca looca) {
        this.looca = looca;
    }
    
    public abstract void executarCaptura();
    
    protected configuracao(String sql, Object... parametros) {
        configuracaoBanco Conexao;
        try (Connection conn = configuracaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < parametros.length; i++) {
                stmt.setObject(i + 1, parametros[i]);
            }
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected void gerarAlerta(String categoria,String descricao,Integer chk_registro,Integer fk_config ){
    
    }
}