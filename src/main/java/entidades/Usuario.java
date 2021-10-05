package entidades;

public class Usuario {

	private String nome;
	
	public Usuario() {}

	
	public Usuario(String nome) {
		this.nome = nome;
	}

	public String getNome() throws Exception {
		if (this.nome == null || this.nome == ""){
			throw new Exception("Nome Vazio");
		}
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Usuario{" +
				"nome='" + nome + '\'' +
				'}';
	}
}