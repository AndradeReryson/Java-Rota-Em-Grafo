package programagrafo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProgramaGrafo {
    static int[][] matriz = {
        {0, 0, 1, 0, 1, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0},
        {1, 0, 0, 0, 0, 0, 1, 0},
        {0, 1, 0, 0, 0, 0, 0, 0},
        {1, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0}, 
        {0, 0, 0, 0, 0, 1, 0, 0}, 
        {0, 1, 0, 0, 0, 0, 0, 0} 
    };
    
    static ArrayList<String> vertices_visit = new ArrayList<>();
    
    // lembre-se: apesar dos vertices começarem no numero 1, a matriz começa no numero 0
    static int origem = 0;
    static int destino = 7;
    static String continuar = "N";
    
    public static void main(String[] args) {
        int qnt_testes = 0;
        
        System.out.println("Matriz: ");
        imprimirMatriz(matriz);
        
        Scanner leitor = new Scanner(System.in);
        
        System.out.println("Vertices existentes: ");
        System.out.println("1 - 2 - 3 - 4 - 5 - 6 - 7 - 8");
        do{
            qnt_testes++;
            System.out.println("\n\n========= teste "+(qnt_testes)+". =====================");
                                                                    
            try{
                System.out.print("Insira o vertice inicial: ");
                origem = leitor.nextInt();
                origem--;
                System.out.print("Insira o vertice de destino: ");
                destino = leitor.nextInt();
                destino--;

                if(origem < 0 || origem > 8){
                    origem = 0;
                }

                if(destino < 0 || destino > 8){
                    destino = 7;
                }
            } catch(InputMismatchException e){
                System.out.println("Valor invalido! Esperavamos um numero inteiro");
                System.exit(1);
            }

            buscarRota(origem, destino);
            
            System.out.println("_______________________________________");
            System.out.print("Deseja testar outra rota? (S/N): ");
            continuar = leitor.next();
            
            if(!continuar.equalsIgnoreCase("S") && !continuar.equalsIgnoreCase("N")){
                continuar = "N";
            }
            
        } while(continuar.equalsIgnoreCase("S"));
    }
    
    public static boolean buscarRota(int origem, int destino){
        vertices_visit.clear(); // limpamos o arrayList de pontos visitados
        System.out.println("Buscando rota de "+(origem+1)+" até "+(destino+1)+"...");
        int ultima_coluna = matriz[0].length - 1;
                
        
        int i = origem;
        
        while(true){
            
            // se na linha atual houver uma conexao ao vertice destino, podemos finalizar a busca
            if(matriz[i][destino] == 1){
                String aux = "("+i+","+destino+")";    // guardamos a lihha e coluna final
                vertices_visit.add(aux);
                
                imprimirRota();
                return true;
            } 
            
            // percorre todas as colunas de uma linha, afim de achar um arco
            for(int j = 0; j<=ultima_coluna; j++ ){
                int index_atual = matriz[i][j];
                String linha_coluna = "("+i+","+j+")";  // criar uma String '(linha,coluna)' para saber se a linha e coluna ja foram comparados antes 
                
                // precisamos saber 3 coisas para dar continuidade:
                // primeiro, se no vertice atual [i] há referencia a outro vertice [j]
                // segundo, se essa posição [i][j] ja foi testada antes;
                // terceiro, se a refencia a outro vertice [j] é diferente da origem, ou seja, se ele apontar pra origem, ignoramos
                if(index_atual == 1
                    && verticeFoiVisitado(linha_coluna) == false
                        && j != origem){
                   
                    vertices_visit.add(linha_coluna);    // adicionamos a posição (i,j) no arraylist 
                    
                    i = j;  // agora, vamos continuar a verificar os arcos a partir do vertice que encontramos 
                    break;
                } else {
                    if(j == ultima_coluna){
                        
                        // se ja percoremos todas as colunas da linha [i], e ela for o vertice origem, significa que não há conexões 
                        if(i == origem){
                            imprimirRota();
                            System.out.println("Não existe uma rota entre os vertices");
                            return false;
                        }
                        
                        vertices_visit.add("Fim da linha_");
                        
                        i = origem;
                        break;
                        
                    }
                }
            }
        }
    }
   
    public static void imprimirRota(){
        int tamanho_vt = vertices_visit.size(); // tamanho do arrayList que guarda o (i,j) visitados
        int ultimo_index = tamanho_vt-1;
        
        String rotas = "";  // vamos unir todas as posições visitadas em uma unica linha
        
        System.out.println("\n> Rotas testadas:");
        
        for(int i=0; i<tamanho_vt; i++){
            String pos = vertices_visit.get(i);
            
            // caso o ponto dentro do vetor for uma posição (i,j) e não um fim da linha
            if(!pos.equals("Fim da linha_")){
                rotas += Integer.parseInt(pos.substring(1,2))+1; // da (linha, coluna) pegamos só a linha, que é o vertice. Porem, somamos +1 ja que a linha começa no 0
               
                if(i != ultimo_index){
                    rotas += " --> ";
                } else {
                    rotas += " --> "+(destino+1)+" (sucesso)";
                }
                
            } else {
                rotas += pos; // no caso de ser o texto "Fim da linha_" vamos apenas adiciona-lo ao fim da rota, não precisamos 
            }
            
            
        }
        
        String[] rotas_separadas = rotas.split("_");
        for(int j=0; j<rotas_separadas.length; j++){
            System.out.println(rotas_separadas[j]);
        }
       
    }
    
    public static void imprimirMatriz(int[][] matriz){
        int tamanho_linha = matriz[0].length;
        
        System.out.println("     (1) (2) (3) (4) (5) (6) (7) (8)");
        for(int i = 0; i<matriz.length; i++){
          
            System.out.print("("+(i+1)+") " );
            for(int j = 0; j<tamanho_linha; j++){
                System.out.print("  "+matriz[i][j]+" ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    public static boolean verticeFoiVisitado(String vertice){
        return vertices_visit.contains(vertice);
    }
}

