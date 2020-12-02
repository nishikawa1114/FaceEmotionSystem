import React from 'react';
import './index.css';

interface emotion {
    anger: number;
    contempt: number;
    disgust: number;
    fear: number;
    happiness: number;
    neutral: number;
    sadness: number;
    surprise: number;
}

interface TableProps {
    emotion: emotion;
}

export class Table extends React.Component<TableProps> {

    render() {

        return (
            <div>
                <table>
                    <tr>
                        <th>感情</th>
                        <th>値</th>
                    </tr>
                    {Object.entries(this.props.emotion).map(([key, value]) => (

                        <tr >
                            <td className="emotion">{key}</td>
                            <td className="emotion_value">{value}</td>
                        </tr>

                    ))}
                </table>
            </div>
        )
    }
}