package decaf;
import org.antlr.symtab.FunctionSymbol;
import org.antlr.symtab.GlobalScope;
import org.antlr.symtab.LocalScope;
import org.antlr.symtab.Scope;
import org.antlr.symtab.VariableSymbol;
import org.antlr.symtab.Symbol;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import decaf.DecafParser.StatementContext;
import java.util.ArrayList;
import org.antlr.symtab.ParameterSymbol;

/**
 * This class defines basic symbols and scopes for Decaf language
 */
public class DecafSymbolsAndScopes extends DecafParserBaseListener {
    ParseTreeProperty<Scope> scopes = new ParseTreeProperty<Scope>();
    ArrayList<String> varlist = new ArrayList();
    ArrayList<String> escopo = new ArrayList();
    ArrayList<String> metodo = new ArrayList();
    GlobalScope globals;
    Scope currentScope; // define symbols in this scope
    Boolean a;
    String typeM;

    @Override
    public void enterProgram(DecafParser.ProgramContext ctx) {
        globals = new GlobalScope(null);
        pushScope(globals);
    }

    @Override
    public void exitProgram(DecafParser.ProgramContext ctx) {
        if (globals.resolve("main") == null)
        {
            this.error(ctx.RCURLY().getSymbol(), "Método \'main\' não encontrado.");
            System.exit(0);
        }
        popScope();
        //System.out.println(globals);
    }

    @Override
    public void enterMethod_decl(DecafParser.Method_declContext ctx) {
        String name = ctx.ID().getText();
        this.a = false;
        this.metodo.add(name +","+ ctx.type_id().size()); // {nome,quantidade de parametros}
        //int typeTokenType = ctx.type().start.getType();
        //DecafSymbol.Type type = this.getType(typeTokenType);

	   for(int i=0;i<ctx.type_id().size();i++){
            this.escopo.add(name +","+ ctx.type_id().get(i).type().getText()); // {nome, posição do parametro, tipo do parametro}

        }

        try{
            this.typeM = ctx.type().getText();
        }catch (Exception e){}

        try{
            if(ctx.T_VOID().getText().equals("void")){
                this.a = true;
            }
        }catch (Exception e){}


        // push new scope by making new one that points to enclosing scope
        FunctionSymbol function = new FunctionSymbol(name);
        // function.setType(type); // Set symbol type
        

        currentScope.define(function); // Define function in current scope
        saveScope(ctx, function);
        pushScope(function);
    }

    @Override
    public void exitMethod_decl(DecafParser.Method_declContext ctx) {
        try{

            if(a == true){
                for(int i=0;i<ctx.block().statement().size();i++){
                    if(ctx.block().statement().get(i).T_RETURN().getText().equals("return")){
                        this.error(ctx.block().statement().get(i).T_RETURN().getSymbol(),"should not return value");
                        System.exit(0);
                    }
                }
            }else{ 

                for(int i=0;i<ctx.block().statement().size();i++){
                    if(ctx.block().statement().get(i).T_RETURN().getText().equals("return")){
                        for(int j=0;j<ctx.block().statement().get(i).expr().size();j++) {

                            //entra no literal (boolean ou int)
                            if(ctx.block().statement().get(i).expr().get(j).literal() != null){

                                String literal = ctx.block().statement().get(i).expr().get(j).literal().getText();

                                //caso o tipo seja 'boolean' e receba um valor diferente
                                if(this.typeM.equals("boolean") && !(literal.equals("false") || literal.equals("true"))){
                                    this.error(ctx.ID().getSymbol(),"return value has wrong type");
                                    System.exit(0);
                                }

                                //caso o tipo seja 'int' e receba um valor diferente
                                if(this.typeM.equals("int") && !(this.numbers(literal))){
                                    this.error(ctx.ID().getSymbol(),"return value has wrong type");
                                    System.exit(0);
                                }
                            }

                        }
                    }
                }
            }
        }catch (Exception e){}
        popScope();
    }

    /*@Override
    public void enterBlock(DecafParser.BlockContext ctx) {
        LocalScope l = new LocalScope(currentScope);
        saveScope(ctx, currentScope);
        pushScope(l);
    }

    @Override
    public void exitBlock(DecafParser.BlockContext ctx) {
        popScope();
    }*/
    @Override public void enterStatement(DecafParser.StatementContext ctx) { 
        try{
            String var = ctx.location().ID().getText();
            if(! varlist.contains(var) ) {
                this.error(ctx.location().ID().getSymbol(), "Variavel não declarada");
                System.exit(0);
            }

        }catch (Exception e) {}
    }
    

    @Override public void enterMethod_call(DecafParser.Method_callContext ctx){
    }

    @Override public void exitMethod_call(DecafParser.Method_callContext ctx){
        try{
            for(int i = 0 ; i < ctx.expr().size(); i++){
            if(!this.metodo.contains(ctx.method_name().ID().getText()+","+ ctx.expr().size())){ 
                this.error(ctx.expr(0).literal().int_literal().decimal_literal().NUM().getSymbol(),"argument mismatch");
                System.exit(0);
            }    
        }
    }catch(Exception e){}

        try{
        for(int i=0;i<ctx.expr().size();i++) {

                if(ctx.expr().get(i).literal() != null){
    }
                    if(ctx.expr().get(i).literal().getText().equals("false") || ctx.expr().get(i).literal().getText().equals("true")){
                        //para boolean
                        if(!this.escopo.contains(ctx.method_name().ID().getText()+","+ i)){
                            this.error(ctx.expr(0).literal().bool_literal().BOOLEANLITERAL().getSymbol(),"types don't match signature");
                            System.exit(0);
                        }
                    }else{
                        //para int
                        if(!this.escopo.contains(ctx.method_name().ID().getText()+","+ i)) {
                            this.error(ctx.expr(1).literal().int_literal().decimal_literal().NUM().getSymbol(),"types don't match signature");
                            System.exit(0);
                        }
                    }   
                }
            }catch(Exception e){}
        }
    

    @Override
    public void enterType_id(DecafParser.Type_idContext ctx) {
        //for (int i = 0; i < ctx.ID().size(); i++ ){
        defineVar(ctx.type(), ctx.ID().getSymbol());
        this.varlist.add(ctx.ID().getText());
    }


    @Override
    public void exitType_id(DecafParser.Type_idContext ctx) {
        //for (int i = 0; i < ctx.ID().size(); i++ ){
        String name = ctx.ID().getSymbol().getText();
        Symbol defineVar = currentScope.resolve(name);
        if ( defineVar == null ) {
            this.error(ctx.ID().getSymbol(), "no such variable: "+name);
        }
        if ( defineVar instanceof FunctionSymbol ) {
            this.error(ctx.ID().getSymbol(), name+" is not a variable");
        //}
    }
}
    @Override
    public void enterVar_decl(DecafParser.Var_declContext ctx) {
        String variavel_local = "";
        for (int i = 0; i < ctx.ID().size(); i++ ){
        variavel_local = variavel_local + ctx.ID().get(i).getText();
        defineVar(ctx.type(), ctx.ID().get(i).getSymbol());
        this.varlist.add(ctx.ID().get(i).getText());
    }
}


    @Override
    public void exitVar_decl(DecafParser.Var_declContext ctx) {
        for (int i = 0; i < ctx.ID().size(); i++ ){
        String name = ctx.ID().get(i).getSymbol().getText();
        Symbol defineVar = currentScope.resolve(name);
        if ( defineVar == null ) {
            this.error(ctx.ID().get(i).getSymbol(), "no such variable: "+name);
        }
        if ( defineVar instanceof FunctionSymbol ) {
            this.error(ctx.ID().get(i).getSymbol(), name+" is not a variable");
        //}
        }
    }
}
    @Override
    public void enterField_decl(DecafParser.Field_declContext ctx) {
            try{
                if(Integer.parseInt(ctx.int_literal().getText()) <= 0) {
                    this.error(ctx.int_literal().decimal_literal().NUM().getSymbol(), "Bad Array Size");
                    System.exit(0);
                }  

           }catch (Exception e){}
}


    @Override
    public void exitField_decl(DecafParser.Field_declContext ctx) {
        for (int i = 0; i < ctx.ID().size(); i++ ){
        String name = ctx.ID().get(i).getSymbol().getText();
        Symbol defineVar = currentScope.resolve(name);
        
        if ( defineVar == null ) {
            this.error(ctx.ID().get(i).getSymbol(), "no such variable: "+name);
        }
        if ( defineVar instanceof FunctionSymbol ) {
            this.error(ctx.ID().get(i).getSymbol(), name+" is not a variable");
        }
    }
}

    
    void defineVar(DecafParser.TypeContext typeCtx, Token nameToken) {
        //int typeTokenType = typeCtx.start.getType();
        VariableSymbol var = new VariableSymbol(nameToken.getText());

        // DecafSymbol.Type type = this.getType(typeTokenType);
        // var.setType(type);

        currentScope.define(var); // Define symbol in current scope
    }

    /**
     * Método que atuliza o escopo para o atual e imprime o valor
     *
     * @param s
     */
    private void pushScope(Scope s) {
        currentScope = s;
        System.out.println("entering: "+currentScope.getName()+":"+s);
    }

    /**
     * Método que cria um novo escopo no contexto fornecido
     *
     * @param ctx
     * @param s
     */
    void saveScope(ParserRuleContext ctx, Scope s) {
        scopes.put(ctx, s);
    }

    /**
     * Muda para o contexto superior e atualiza o escopo
     */
    private void popScope() {
        System.out.println("leaving: "+currentScope.getName()+":"+currentScope);
        currentScope = currentScope.getEnclosingScope();
    }

    public static void error(Token t, String msg) {
        System.err.printf("line %d:%d %s\n", t.getLine(), t.getCharPositionInLine(),msg);
    }

    /**
     * Valida tipos encontrados na linguagem para tipos reais
     *
     * @param tokenType
     * @return
     */
    public static DecafSymbol.Type getType(int tokenType) {
        switch ( tokenType ) {
            case DecafParser.T_VOID :  return DecafSymbol.Type.tVOID;
            case DecafParser.T_INT :   return DecafSymbol.Type.tINT;
            case DecafParser.T_BOOLEAN :   return DecafSymbol.Type.tBOOLEAN;
        }
        return DecafSymbol.Type.tINVALID;
    }
    /**
     * Verifica se um texto só tem números
     *
     * @param texto
     * @return
     */

    public boolean numbers(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            if (!Character.isDigit(texto.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
