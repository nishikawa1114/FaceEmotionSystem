import React from 'react';
import './index.css';
import { Emotion } from './types';


interface TableProps {
    emotion: Emotion;
}

export class Table extends React.Component<TableProps> {

    render() {

        return (
            <div>
                <table key="emotion_table">
                    <tr key="table_header">
                        <th>感情</th>
                        <th>値</th>
                    </tr>
                    {Object.entries(this.props.emotion).map(([key, value]) => (

                        <tr>
                            <td className="emotion">{key}</td>
                            <td className="emotion_value">{value}</td>
                        </tr>

                    ))}
                </table>
            </div>
        )
    }
}