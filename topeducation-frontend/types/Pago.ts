export default interface Pago {
  id: number,
  total: number,
  idEstudiante: number,
  fecha: string,
  idsRazones: Array<number>
}