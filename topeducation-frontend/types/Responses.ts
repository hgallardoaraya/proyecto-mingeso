import Estudiante from "./Estudiante";
import Razon from "./Razon";

export interface Response {
  message: string;
  status: number;
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