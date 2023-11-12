import TablaRazones from "./estudiante/[id]/TablaRazones";
import VerRazonesPorRutForm from "./VerRazonesPorRutForm";

export default function RazonesPage(){
    return (
      <div className="px-5">
        <h1 className="font-bold mb-2">Listar razones por RUT</h1>
        <VerRazonesPorRutForm/>
      </div>
    )
}