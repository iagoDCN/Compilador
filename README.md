# ANTLR Project
- O ANTLR (outra ferramenta para reconhecimento de idiomas) é um poderoso gerador de analisador para ler, processar, executar ou traduzir texto estruturado ou arquivos binários. É amplamente usado para criar linguagens, ferramentas e estruturas. A partir de uma gramática, o ANTLR gera um analisador que pode construir e percorrer árvores de análise.

# Para executar é Simples( Rode o programa no Linux)
- 1º_ Sertifique-se que o java 1.6 ou superior está instalado na sua máquina.

- 2º_ De git clone para clonar o repositório

- 3º_ Entre na pasta da seguinte forma Compilador/provided/skeleton

- 4º_ Use o comando "ant" para compilar o projeto

- 5º_ Use o comando para executar java -jar dist/Compiler.jar -target scan -debug ../scanner/nome_arquivo. 

OBS: 
 - Todos os arquivos estão na pasta Scanner.

OBS2: 
 - Caso os camandos acima não funcionem, utilize os passos abaixo:
 
 # INSTALANDO ANTLR
 - cd /usr/local/lib
 
 - sudo curl -O https://www.antlr.org/download/antlr-4.7.2-complete.jar
 
 - export CLASSPATH=".:/usr/local/lib/antlr-4.7.2-complete.jar:$CLASSPATH"
 
 - alias antlr4='java -jar /usr/local/lib/antlr-4.7.2-complete.jar'
 
 - alias grun='java org.antlr.v4.gui.TestRig'
 
 OBS3: Pronto ! Agora só seguir os passos de exucução.

