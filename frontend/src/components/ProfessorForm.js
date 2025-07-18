import { useState } from 'react';
import axios from 'axios';

export default function ProfessorForm({ onAdd }) {
  const [name, setName] = useState('');
  const submit = e => {
    e.preventDefault();
    axios.post('/api/professors', { name })
      .then(res => { onAdd(res.data); setName(''); });
  };
  return (
    <form onSubmit={submit} className="d-flex mb-3">
      <input
        className="form-control me-2"
        value={name}
        onChange={e => setName(e.target.value)}
        placeholder="Имя профессора"
        required
      />
      <button className="btn btn-success">Add</button>
    </form>
  );
}