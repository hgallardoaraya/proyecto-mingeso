import EntradaReporte from "./EntradaReporte";
import Estudiante from "./Estudiante";
import Pago from "./Pago";
import Razon from "./Razon";

export interface Response {
  message: string | null;
  status: number | null;
}

export interface ResponseEstudiante extends Response {
  estudiante: Estudiante;
}

export interface ResponseEstudiantes extends Response {
  estudiantes: Array<Estudiante>;
}

export interface ResponseRazones extends Response {
  razones: Array<Razon>;
}

export interface ResponsePago extends Response {
  pago: Pago | null;
}

export interface ResponseResumen extends Response {
  reporte: Array<EntradaReporte>;
}