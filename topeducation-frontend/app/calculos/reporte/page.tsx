import EntradaReporte from "@/types/EntradaReporte";
import obtenerReporte from "./actions"
import { ResponseResumen } from "@/types/Responses";

export default async function ReporteResumenPage(){
  const response:ResponseResumen = await obtenerReporte();

  return(
    <table>    
      <thead>
      <tr>
          <th>Rut</th>
          <th>N° exámenes rendidos</th>
          <th>Promedio puntaje exámenes</th>
          <th>Total arancel</th>
          <th>Tipo pago</th>
          <th>N° cuotas pactadas</th>
          <th>N° cuotas pagadas</th>
          <th>Arancel pagado</th>
          <th>Total pagado</th>
          <th>Fecha último pago</th>
          <th>Arancel por pagar</th>
          <th>Total por pagar</th>
          <th>N° cuotas atrasadas</th>
      </tr>
    </thead>
    <tbody>
      {response.reporte.map((entrada:EntradaReporte, index:number) => (
        <tr key={index}>
            <td>{entrada.rut}</td>
            <td>{entrada.numeroExamenesRendidos}</td>
            <td>{entrada.promedioExamenes}</td>
            <td>{entrada.totalArancel}</td>
            <td>{entrada.tipoPago}</td>
            <td>{entrada.numeroCuotasPactadas}</td>
            <td>{entrada.numeroCuotasPagadas}</td>
            <td>{entrada.arancelPagado}</td>
            <td>{entrada.totalPagado}</td>
            <td>{entrada.fechaUltimoPago}</td>
            <td>{entrada.saldoArancelPendiente}</td>
            <td>{entrada.saldoTotalPendiente}</td>
            <td>{entrada.numeroCuotasAtrasadas}</td>
        </tr>
      ))}
    </tbody>
  </table>
  )
}