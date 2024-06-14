package model;

import com.google.gson.annotations.SerializedName;

/** record DadosMoeda é utiliada em conjunto com o gson para fazer
 * o mapeamento automático dos campos Json, com o @SerializedName
 *
 * @param resultadoDaConversao
 */
public record DadosMoeda(
        @SerializedName("conversion_result") double resultadoDaConversao
) {
    @Override
    public double resultadoDaConversao() {
        return resultadoDaConversao;
    }
}
