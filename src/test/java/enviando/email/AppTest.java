package enviando.email;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@org.junit.Test
	public void testeEmail() throws Exception {

		StringBuilder stringBuilderTextoEmail = new StringBuilder();
		stringBuilderTextoEmail.append("Olá, <br/><br/>");
		stringBuilderTextoEmail.append("Você esta recebendo um teste de envio de email com html <br/><br/>");
		stringBuilderTextoEmail.append("Acesse um bom site clicando no botão abaixo <br/> <br/>");

		stringBuilderTextoEmail.append("<b>Login:</b> guilherme@gmail.com <br/>");
		stringBuilderTextoEmail.append("<b>Senha:</b> senhadoemail123 <br/><br/> ");

		stringBuilderTextoEmail.append(
				"<a target =\"_blank\" href=\"http://www.google.com\"style=\"color:#2525a7; padding:14px 25px; text-align:center; text-decoration:none; display:inline-block; border-radius:30px; font-size:20px; font-family:courier; border:3px solid green; background-color:#99DA39;\" >Acessar esse site<a/><br/><br/>");

		stringBuilderTextoEmail.append("<span style=\"font-size:8px\">Ass.: Guilherme Paiva </span>");

		ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("guifoxx65@gmail.com", "Guilherme Paiva",
				"Teste de envio de Email", stringBuilderTextoEmail.toString());

		enviaEmail.enviarEmailAnexo(true);

	}

}
