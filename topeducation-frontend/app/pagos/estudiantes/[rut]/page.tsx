import { ResponseRazones } from "@/types/Responses";
import VerRazonesPagoPorRutForm from "../VerRazonesPagoPorRutForm";
import PagarRazonesForm from "./PagarRazonesForm";
import { obtenerRazonesPendientesPorRut } from "@/app/cuotas/actions";
import pagarRazones from "./actions";

export default async function PagarRazonesPorRut(props: any){
  const response: ResponseRazones = await obtenerRazonesPendientesPorRut(props.params.rut);  
  return(
    <div>
      <h1 className="px-3 font-bold">Pagar razones</h1>
      {
        !response.razones || response.razones.length < 1 ?
        (<p>No hay razones de pago pendientes.</p>)
        :
        <PagarRazonesForm rut={ props.params.rut } razones={ response.razones }/>      
      }
    </div>
  )
}