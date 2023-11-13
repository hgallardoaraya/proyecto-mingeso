import { obtenerRazonesPendientesPorRut } from "@/app/cuotas/actions";
import { ResponseRazones } from "@/types/Responses";
import PagarRazonesForm from "../../PagarRazonesForm";

export default async function PagarRazonesEstudiantePage(props: any){
  const response: ResponseRazones = await obtenerRazonesPendientesPorRut(props.params.rut);
  
  return(
    <div>
      <h1>Pagar razones estudiante { props.params.rut }</h1>
      <div>
        <PagarRazonesForm razones={response.razones}/>
      </div>
    </div>
  ) 
}