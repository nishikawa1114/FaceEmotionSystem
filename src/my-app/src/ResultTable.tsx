import { Grid, TableBody, TableCell, TableHead, TableRow } from '@material-ui/core';
import Table from '@material-ui/core/Table';
import React from 'react';
import './index.css';
import { Emotion } from './types';


interface TableProps {
    emotion: Emotion;
}

// 分析結果のemotionの表を表示するコンポーネント
export class ResultTable extends React.Component<TableProps> {

    render() {

        return (

            <Table component={Grid} size="small" aria-label="a dense table" className="table">
                <TableHead>
                    <TableRow>
                        <TableCell align="center">感情</TableCell>
                        <TableCell align="center">値</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody key="emotion">
                    {Object.entries(this.props.emotion).map(([key, value]) => (
                        <TableRow key={key}>
                            <TableCell component="th" scope="row">{key}</TableCell>
                            <TableCell align="right">{value}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>

        )
    }
}