package controller;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TableroFXMLController implements Initializable {


    @FXML
    private Label lbl00, lbl01, lbl02, lbl10, lbl11, lbl12, lbl20, lbl21, lbl22;

    //Nos sirve para tener las celdas seleccionadas y no alterar una ya seleccionada
    private ArrayList<String> arraySeleccionados = new ArrayList();

    private int turnos = 1;//Empezamos con turnos en 1 ya que utilizaremos para definir el numero de jugador
    private final int MAX_TURNOS = 9;//Como es de 3x3 solo hay 9 turnos como maximo
    

    @FXML
    private Text txtJugador;

    private String texto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        texto = txtJugador.getText();
        txtJugador.setText(texto + turnos);
    }

    public void setStage(Stage primaryStage) {
    }

    @FXML
    private void seleccionar(MouseEvent event) {
        if (turnos > MAX_TURNOS) {//Si llegamos al maximo de Turnos ya no entramos a seleccionar mas
            System.err.println("EL JUEGO HA TERMINADO");
        } else {
            // Obtener el nodo que originó el evento
            Object source = event.getSource();
            // Verificar si el nodo es una instancia de Label
            if (source instanceof Label) {
                Label selectedLabel = (Label) source;//Seleccionamos la Label que se esta seleccionando
                if (isSelected(selectedLabel.getId()) == true) {//Si esta en el arrayList entonces le decimos que ese ya esta
                    System.out.println("YA SELECCIONASTE ESTA CASILLA");
                } else {
                    marcar(selectedLabel);//Marcamos con X u O segun el jugador
                    arraySeleccionados.add(selectedLabel.getId());//Lo agregamos a los seleccionados
                    if (itsWin(((turnos + 1) % 2) + 1) == true) {
                        txtJugador.setText("GANO EL JUGADOR " + (((turnos + 1) % 2) + 1));
                        turnos = MAX_TURNOS + 1;
                    } else {
                        turnos++;//Siguiente Jugador
                        if (turnos > MAX_TURNOS) {
                            txtJugador.setText("EL JUEGO SE EMPATO");
                        } else {
                            txtJugador.setText(texto + (((turnos + 1) % 2) + 1));
                        }
                    }
                }
            }
        }
    }

    private void marcar(Label labelMarcar) {
        //Suponemos que el Primer jugador es X y O el 2do.
        if (turnos % 2 == 0)//Como el 2do siempre sus turnos son pares entonces revisamos si es par o impar
            labelMarcar.setText("O");
         else 
            labelMarcar.setText("X");
    }

    //Revisamos si el Label Seleccionado esta dentro del ArrayList de seleccionados
    private boolean isSelected(String lblID) {
        boolean regreso = false;
        for (int i = 0; i < arraySeleccionados.size(); i++) {//Recorremos los seleccionados
            if (arraySeleccionados.get(i).equals(lblID)) {//Comparamos entre cada uno y si esta entonces regresamos true
                regreso = true;
                break;//Rompemos el ciclo para que no siga revisando entre los demas ya que ya se encontro
            }
        }
        return regreso;
    }

    //Le pasamos el jugador actual para verificar quien gano
    private boolean itsWin(int jugadorActual) {
        String jugador;

        if (jugadorActual % 2 == 0)
            jugador = "O";
        else
            jugador = "X";

        //Combinaciones ganadoras
        int[][] combinacionesGanadoras = {
            {0, 1, 2}, // Primera fila
            {3, 4, 5}, // Segunda fila
            {6, 7, 8}, // Tercera fila
            {0, 3, 6}, // Primera columna
            {1, 4, 7}, // Segunda columna
            {2, 5, 8}, // Tercera columna
            {0, 4, 8}, // Diagonal derecha arriba
            {2, 4, 6} // Diagonal izquierda arriba
        };

        String[] etiquetas = {
            lbl00.getText(), lbl01.getText(), lbl02.getText(),
            lbl10.getText(), lbl11.getText(), lbl12.getText(),
            lbl20.getText(), lbl21.getText(), lbl22.getText()
        };

        // Verificar cada combinación ganadora
        for (int[] combinacion : combinacionesGanadoras) {
            if (etiquetas[combinacion[0]].equals(jugador)
                    && etiquetas[combinacion[1]].equals(jugador)
                    && etiquetas[combinacion[2]].equals(jugador)) {
                
                return true; // Se encontró una combinación ganadora
            }
        }
        return false; // No se encontró ninguna combinación ganadora
    }

}
