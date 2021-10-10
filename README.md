# Sistema eCommerce para pequena empresa do ramo alimentício - Repositório da Aplicação Server

Essa é a aplicação que registra, atualiza ou busca requisições no banco enviadas pelos clientes ou pela vendedora através das aplicaçôes Client e Admin.

# 📦 Repositórios integrantes do projeto

| Repositório                                                       | Descrição                                                                          |
| ----------------------------------------------------------------- | ---------------------------------------------------------------------------------- |
| [docesGraces](https://github.com/doceGraceseCommerce/docesGraces) | Apresentação e documentação                                                        |
| [client](https://github.com/doceGraceseCommerce/client)           | Aplicação Client com a interface do cliente                                        |
| [admin](https://github.com/doceGraceseCommerce/admin)             | Aplicação Admin com a interface da vendedora                                       |
| [server](https://github.com/doceGraceseCommerce/server)           | Aplicação Server que recebe as requisições do cliente e da vendedora e armazena ou busca no banco |


# ⚙️ Instruções de Instalação e Uso

<ul>
<li><b>Banco de dados</b></li>
<ul>
<li>Baixe e instale o banco de dados MySQL:</li>
<a href="https://dev.mysql.com/downloads/mysql/">MySQL Community</a>
</ul>
</ul>

<ul>
<li><b>Java</b></li>
<ul>
<li>Baixe e instale o Java JDK 1.8.0_271:</li>
<a href="https://www.oracle.com/br/java/technologies/javase/javase8u211-later-archive-downloads.html">Java Archive Downloads</a>
</ul>
</ul>

<ul>
<li><b>Maven</b></li>
<ul>
<li>Baixe e instale a ferramenta de automação de compilação Maven:</li>
<a href="https://maven.apache.org/download.cgi">Maven</a>
</ul>
</ul>



<ul>
<li><b>Construa a aplicação</b></li>
<ul>
<li>Abra o terminal na raiz desse repositório e execute o comando:
<br/>

```bash
$ mvn package
```

</li>
</ul>
</ul>

<ul>
<li><b>Execute a aplicação</b></li>
<ul>
<li>Abra o terminal na pasta <b>./target</b> gerada pela construção e execute o arquivo <b>.jar</b>:
<br/>

```bash
$ java -jar nome_do_arquivo.jar
```

</li>
</ul>
  
  
</ul>
