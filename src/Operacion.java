public class Operacion {
    private long id;
    private String operacion;
    private double num1;
    private double num2;
    private double resultado;

    public Operacion(long id, String operacion, double num1, double num2, double resultado) {
        this.id = id;
        this.operacion = operacion;
        this.num1 = num1;
        this.num2 = num2;
        this.resultado = resultado;
    }

    public long getId() { return id; }
    public String getOperacion() { return operacion; }
    public double getNum1() { return num1; }
    public double getNum2() { return num2; }
    public double getResultado() { return resultado; }
}
