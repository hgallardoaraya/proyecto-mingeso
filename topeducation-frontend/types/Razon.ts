import EstadoRazon from "./EstadoRazon";
import TipoRazon from "./TipoRazon";

export default interface Razon {
  id: number;
  numero: number;
  monto: number;
  fechaInicio: string;
  fechaFin: string;
  idEstudiante: number;
  tipo: TipoRazon;
  estado: EstadoRazon;
}