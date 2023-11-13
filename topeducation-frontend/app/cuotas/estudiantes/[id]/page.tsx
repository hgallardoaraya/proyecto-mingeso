import TablaRazones from "./TablaRazones";

export default function RazonesPorRutPage(props: any){

  return(
    <div>
      <h1>Razones por rut</h1>
      <TablaRazones rut={ props.params.id }/>
    </div>
  )
}