import { useEffect, useState } from 'react';
import axios from 'axios';
import ProfessorForm from './ProfessorForm';

export default function ProfessorList() {
  const [profs, setProfs] = useState([]);

  useEffect(() => {
    axios.get('/api/professors').then(res => setProfs(res.data));
  }, []);

  const deleteProf = id =>
    axios.delete(`/api/professors/${id}`).then(() =>
      setProfs(profs.filter(p => p.id !== id))
    );

  return (
    <div className="container mt-4">
      <h2>Professors</h2>
      <ProfessorForm onAdd={p => setProfs([...profs, p])} />
      <ul className="list-group mb-3">
        {profs.map(p => (
          <li key={p.id} className="list-group-item d-flex justify-content-between">
            {p.name}
            <button className="btn btn-sm btn-danger" onClick={() => deleteProf(p.id)}>
              Delete
            </button>
          </li>
        ))}
      </ul>
      <a href="/api/report" className="btn btn-primary">
        Скачать отчёт
      </a>
    </div>
  );
}