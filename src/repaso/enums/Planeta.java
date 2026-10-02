package repaso.enums;

//El enum en su propio fichero independiente
public enum Planeta {

 // Constantes con masa (kg) y radio (metros)
 MERCURIO (3.303e+23, 2.4397e6),
 VENUS    (4.869e+24, 6.0518e6),
 TIERRA   (5.976e+24, 6.37814e6),
 MARTE    (6.421e+23, 3.3972e6);

 private final double masa;  // en kilogramos
 private final double radio; // en metros

 Planeta(double masa, double radio) {
     this.masa  = masa;
     this.radio = radio;
 }

 // Constante de gravitación universal
 private static final double G = 6.67300E-11;

 /** Gravedad superficial: g = G * masa / radio^2 */
 public double gravedadSuperficial() {
     return G * masa / (radio * radio);
 }

 /** Peso en Newtons de un objeto en este planeta: Fuerza = masa * gravedad */
 public double peso(double masaObjetoKg) {
     return masaObjetoKg * gravedadSuperficial();
 }
}