import { useState } from 'react';
import { Button, Col, Form, Row } from "react-bootstrap";

type Props = {
    initialValues: ({ [key: string]: string } | string)[];
    type: 'keyValue' | 'string';
    namePrefix: string;
    label: string;
};



const MultiInputGroup = ({ type, namePrefix, label, initialValues = [] }: Props) => {
    const [items, setItems] = useState(initialValues.length > 0 ? initialValues : type === 'keyValue' ? [{ "": "" }] : [""]);
  
    const handleAdd = () => {
      setItems([...items, type === 'keyValue' ? { "": "" } : ""]);
    };
  
    const handleRemove = (index: number) => {
      const next = [...items];
      next.splice(index, 1);
      setItems(next);
    };
  
    return (
      <Form.Group className="mb-3">
        <Form.Label>{label}</Form.Label>
        <Button type="button" variant="link" className="bi bi-plus px-1" onClick={handleAdd} />
        {items.map((item, index) => {
          const key = typeof item === "object" ? Object.keys(item)[0] || "" : "";
          const value = typeof item === "object" ? Object.values(item)[0] || "" : item;
  
          return (
            <Form.Group key={index} controlId={`${namePrefix}-${index}`}>
              <Row className="mb-2 align-items-end">
                {type === 'keyValue' ? (
                  <>
                    <Col md={5}>
                      <Form.Label>Key</Form.Label>
                      <Form.Control
                        type="text"
                        name={`${namePrefix}[${index}].key`}
                        defaultValue={key}
                      />
                    </Col>
                    <Col md={5}>
                      <Form.Label>Value</Form.Label>
                      <Form.Control
                        type="text"
                        name={`${namePrefix}[${index}].value`}
                        defaultValue={value}
                      />
                    </Col>
                  </>
                ) : (
                  <Col md={10}>
                    <Form.Control
                      type="text"
                      name={`${namePrefix}[${index}]`}
                      defaultValue={value}
                    />
                  </Col>
                )}
                <Col md={2}>
                  {items.length > 1 && (
                    <Button type="button" variant="link" className="bi bi-dash px-0" onClick={() => handleRemove(index)} />
                  )}
                </Col>
              </Row>
            </Form.Group>
          );
        })}
      </Form.Group>
    );
  };

  export default MultiInputGroup;

  