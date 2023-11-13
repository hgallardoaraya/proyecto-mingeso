export default interface EntradaReporte {
  rut:string;
  numeroExamenesRendidos:number;
  promedioExamenes:number;
  totalArancel:number;
  tipoPago:string;
  numeroCuotasPactadas:number;
  numeroCuotasPagadas:number;
  arancelPagado:number;
  totalPagado:number;
  fechaUltimoPago:string;
  saldoArancelPendiente:number;
  saldoTotalPendiente:number;
  numeroCuotasAtrasadas:number;
}