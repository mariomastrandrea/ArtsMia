package it.polito.tdp.artsmia.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model 
{
	private Graph<ArtObject, DefaultWeightedEdge> grafo;
	private final ArtsmiaDAO artsmiaDao;
	private final Map<Integer, ArtObject> artObjectsIdMap;

	
	public Model()
	{
		this.artsmiaDao = new ArtsmiaDAO();
		this.artObjectsIdMap = new HashMap<>();
	}
	
	public void creaGrafo()
	{
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungere i vertici
		//1. recupero tutti gli ArtObject dal db
		//2. li inserisco nel grafo come vertici
		this.artsmiaDao.listObjects(artObjectsIdMap);
		Graphs.addAllVertices(this.grafo, artObjectsIdMap.values());
		
		//aggiungere gli archi
		//ci sono 3 approcci:
		// 1. meno operazioni nel db e più operazioni lato codice; doppio ciclo for annidato,
		//		confrontando tutte le coppie di vertici, e verificando tramite query al db se 
		//		collegarli o meno (ed eventualmente come) -> approccio (molto) meno efficiente  
		// 2. Per ciascun oggetto, faccio una query per ricavare gli oggetti a cui esso è collegato
		//		quindi blocco un oggetto, e trovo i suoi vicini (basta un solo ciclo for)
		// 3. Mi faccio dare direttamente dal db le coppie cercate (approccio più efficiente)
		
		
		// approccio 1 (meno efficiente)
		/*
		for(ArtObject a1 : this.grafo.vertexSet())
		{
			for(ArtObject a2 : this.grafo.vertexSet())
			{
				// controllo che i due oggetti siano diversi e che l'arco in questione non sia già stato inserito
				if(!a1.equals(a2) && !this.grafo.containsEdge(a1, a2))
				{
					//controllo se devo collegare a1 ad a2, facendo una query al db, tramite il dao
					int pesoArco = this.artsmiaDao.getPesoArcoTra(a1, a2);
					
					if(pesoArco > 0)
						Graphs.addEdge(this.grafo, a1, a2, pesoArco);
				}
			}
		}		
		Ci mette circa 2 anni ...
		*/
		
		// approccio 3 (il più efficiente)
		
		Collection<Adiacenza> adiacenze = this.artsmiaDao.getAdiacenze();
		
		for(Adiacenza a : adiacenze)
		{
			ArtObject a1 = artObjectsIdMap.get(a.getId1());
			ArtObject a2 = artObjectsIdMap.get(a.getId2());
			int peso = a.getPeso();
			
			Graphs.addEdge(this.grafo, a1, a2, peso);
		}
	}
	
	public int getNumVertices()
	{
		return this.grafo.vertexSet().size();
	}
	
	public int getNumEdges()
	{
		return this.grafo.edgeSet().size();
	}

	public Collection<ArtObject> computeConnectedComponentOf(int objectId)
	{
		ArtObject source = this.artObjectsIdMap.get(objectId);
		
		if(source == null)
			return null;
		
		Set<ArtObject> connectedComponentVertices = new HashSet<>();
		
		GraphIterator<ArtObject, DefaultWeightedEdge> iterator = 
							new BreadthFirstIterator<>(this.grafo, source);

		while(iterator.hasNext())
		{
			ArtObject nextObj = iterator.next();
			connectedComponentVertices.add(nextObj);
		}
		
		return connectedComponentVertices;
	}
	
}
