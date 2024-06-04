package com.example.emplostaff2;

public class ValidadorNIF {
    public static Boolean validador(String NIF){//49451301J
        String nif_valido="";
        String[] letras= {"T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"};
        String letra=NIF.substring(8,9);
        Integer numero=Integer.valueOf(NIF.substring(0,8));
        Integer resto=numero%23;
        if (!letra.equals(letras[resto])){
            return false;
        }else {
            return true;}

    }
}
