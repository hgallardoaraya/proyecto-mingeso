import TipoColegio from "./TipoColegio";
import TipoPagoArancel from "./TipoPagoArancel";

export default interface Estudiante {
  id: string | number;
  rut: string;
  nombre1: string;
  nombre2: string;
  apellido1: string;
  apellido2: string;
  fechaNacimiento: string;
  anioEgreso: string;
  cuotasPactadas: string | number;
  nombreColegio: string;
  tipoColegio: TipoColegio;
  tipoPagoArancel: TipoPagoArancel;
}