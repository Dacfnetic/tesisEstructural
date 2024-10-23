/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beam;

/**
 *
 * @author diego
 */
public class Beam {
    
    private double b; // Esta es la dimensión horizontal de la viga.
    private double h; // Esta es la dimensión vertical de la viga.
    private double r; // Este es el recubrimiento desde la arista de la viga hasta el refuerzo transversal.
    private double fc; // Esta es la resistencia a esfuerzos de compresión del concreto.
    private double fy; // Esta es la resistencia a la fluencia del acero de refuerzo longitudinal.
    private double Es = 29000000; // Este es el módulo de elasticidad del acero normalmente se toma como 29 000 000 psi.
    private double Pc = 150; // Peso propio del concreto armado normalemente 150 lb/ft^3.
    private double B1 = 0.85; // B1 de la viga del concreto depende de la resistencia del concreto a compresión.
    private double pbalanceada; // Cuantilla balanceada.
    private double p;
    
    public void setBeamBase(double base) {this.b =  base;}
    public void setBeamHeight(double height) {this.h = height;}
    public void setBeamCover(double cover) {this.r = cover;}
    public void setBeamCompressionStrenght(double fc) {this.fc = fc;}
    public void setBeamLongitudinalYieldingStrenght(double fy) {this.fy = fy;}
    public void setBeamLongitudinalElasticModulus(double Es) {this.Es = Es;}
    public void setSpecificWeightOfReinforcedConcrete(double Pc) {this.Pc = Pc;}
    public void setB1(){
        // Calculo de B1 dependiendo de la resistencia a la compresión del concreto.
        if(fc <= 8000 && fc > 4000){
            B1 = 0.85 - 0.05 * ((fc-4000)/1000);
        }else if(fc > 8000){
            B1 = 0.65;
        }
    }
    public void setPBalanceada(){
        // Calculo de cuantilla balanceada
        double pbalanceada = B1 * (0.85*fc/fy)*(87000/(87000+fy));
    }
    public void setP(double p) {this.p = p;}
    
    public double getBeamBase() {return this.b;}
    public double getBeamHeight() {return this.h;}
    public double getBeamCover() {return this.r;}
    public double getBeamCompressionStrenght() {return this.fc;}
    public double getBeamLongitudinalYieldingStrenght() {return this.fy;}
    public double getBeamLongitudinalElasticModulus() {return this.Es;}
    public double setSpecificWeightOfReinforcedConcrete() {return this.Pc;}

    public void setParams(double b, double h, double r, double fc, double fy){
        setBeamBase(b);
        setBeamHeight(h);
        setBeamCover(r);
        setBeamCompressionStrenght(fc);
        setBeamLongitudinalYieldingStrenght(fy);
    }
    
    public String calculateNominalMoment(double b, double h, double r, double As, double fc, double fy, double Es){
        double d = h - r;
        // Calculo de la relación de acero con respecto al concreto también
        // llamada cuantilla de acero.
        double p = As/(b*d);
        // Calculo de la cuantilla mínima.
        double pmin = 200/fy;
        // Si la cuantilla es menor que la cuantilla mínima la sección no es
        // satisfactoria, se debe incrementar la cuantilla
        if(p < pmin) return "Sección subreforzada. Se debe incrementar la cuantilla de acero."; // Sección sub reforzada
        // Calculo de B1 dependiendo de la resistencia a la compresión del concreto.
        setB1();
        // Calculo de cuantilla balanceada
        setPBalanceada();
        // Si la cuantilla es mayor que el 75 porciento de la cuantilla
        // balanceada la sección no es satisfactoria, se debe aumentar la
        // sección.
        if(p > 0.75 * pbalanceada) return "Sección sobrereforzada. Se debe incrementar la sección de la viga."; // Sección sobre reforzada
        // Calculo de a
        double a = (As*fy)/(0.85*fc*b);
        // Calculo del momento nominal
        double Mn = As*fy*(d-(a/2));
        // Retorno del momento nominal
        return "El momento nominal de la viga es de: " + Mn/12 + " lb * ft";
    }
  
    public String designSimpleReinforcedBeam(double LL, double l){
        boolean calculando = true;
        // Calculo de B1 dependiendo de la resistencia a la compresión del concreto.
        setB1();
        // Calculo de cuantilla balanceada
        setPBalanceada();
        // suponer cuantilla como un porcentaje de la cuantil
        setP(0.5 * pbalanceada);
        do{
            double DL = calculateBeamSelfWeight(l);
            double Wu = 1.4 * DL + 1.7 * LL;
            double Mu = 12*Wu*l*l/8;
            double Mn = Mu/0.9;
            
           
            calculando = false;
        }while(calculando);
        
        return "hola";
    }
    
    
    
    
    public double calculateBeamSelfWeight(double l){
        double Bw = b * h * l * Pc / 144; 
        return Bw;
    }
    
    
    
    
    
    public String seleccionarViga(double largo, double areaTributaria){
        if(largo > 4.5d) return "Esa viga es muy larga para el método simplificado";
        if(areaTributaria > 13d) return "Esa viga carga es cargada por demasiada área para el método simplificado";
        
        double base = 5;
        double alto = 5;
        String aceroSuperior = "";
        String aceroInferior = "";
        String estribos = "";
        
        return "Usar una viga de ancho " + base + " cm. con un alto de " + alto +
               " cm. Incluyendo el espesor de la losa. En la parte superior de la viga usar " + aceroSuperior +
               ".  En la parte inferior de la viga usar " + aceroInferior + " y de estribos usar" + estribos;
    
    }
}
