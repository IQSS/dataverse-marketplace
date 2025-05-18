import { Button, Col, Form, Row } from "react-bootstrap";
import { FormInputTextField, FormInputSelect } from "../../../UI/FormInputFields";

type ObjectSchemaField = {
  name: string;
  label: string;
  type: "string" | "number" | "select";
  options?: string[];
};

type Props = {
  type: "string" | "keyValue" | "object";
  namePrefix: string;
  label: string;
  values: any[];
  onChange: (e: React.ChangeEvent<any>) => void;
  objectSchema?: ObjectSchemaField[];
};

const MultiInputGroup = ({
  type,
  namePrefix,
  label,
  values = [],
  onChange,
  objectSchema = [],
}: Props) => {

  const defaultItem = () => {
    if (type === "keyValue") return { key: "", value: "" };
    if (type === "object") {
      const obj: any = {};
      objectSchema.forEach((field) => {
        obj[field.name] = field.type === "select" ? field.options?.[0] ?? "" : "";
      });
      return obj;
    }
    return "";
  };

  const triggerChange = (newValues: any[]) => {
    const fakeEvent = {
      target: {
        name: namePrefix,
        value: newValues,
      }
    } as unknown as React.ChangeEvent<any>;

    onChange(fakeEvent);
  };

  const handleAdd = () => {
    triggerChange([...values, defaultItem()]);
  };

  const handleRemove = (index: number) => {
    const next = [...values];
    next.splice(index, 1);
    triggerChange(next);
  };

  return (
    <Form.Group className="mb-3">
      <Form.Label>{label}</Form.Label>
      <Button type="button" variant="link" className="bi bi-plus px-1" onClick={handleAdd} />

      {values.map((item, index) => {
        const isObject = typeof item === "object" && !Array.isArray(item);

        const key = type === "keyValue"
          ? (item?.key ?? (isObject ? Object.keys(item)[0] : ""))
          : undefined;

        const value = type === "keyValue"
          ? (item?.value ?? (isObject ? Object.values(item)[0] : ""))
          : undefined;

        return (
          <Form.Group key={index} controlId={`${namePrefix}-${index}`}>
            <Row className="mb-2 align-items-end">
              {type === "string" && (
                <Col md={10}>
                  <FormInputTextField
                    label={`Item ${index + 1}`}
                    name={`${namePrefix}[${index}]`}
                    id={`${namePrefix}-${index}`}
                    value={item}
                    onChange={onChange}
                  />
                </Col>
              )}

              {type === "keyValue" && (
                <>
                  <Col md={5}>
                    <FormInputTextField
                      label="Key"
                      name={`${namePrefix}[${index}].key`}
                      id={`${namePrefix}-${index}-key`}
                      value={key}
                      onChange={onChange}
                    />
                  </Col>
                  <Col md={5}>
                    <FormInputTextField
                      label="Value"
                      name={`${namePrefix}[${index}].value`}
                      id={`${namePrefix}-${index}-value`}
                      value={value}
                      onChange={onChange}
                    />
                  </Col>
                </>
              )}

              {type === "object" &&
                objectSchema.map((field) => {
                  const fieldValue = item?.[field.name] ?? "";

                  return (
                    <Col key={field.name} md={12 / objectSchema.length}>
                      {field.type === "select" ? (
                        <FormInputSelect
                          label={field.label}
                          name={`${namePrefix}[${index}].${field.name}`}
                          id={`${namePrefix}-${index}-${field.name}`}
                          value={fieldValue}
                          onChange={onChange}
                          options={field.options?.map((o) => ({
                            value: o,
                            label: o,
                          })) || []}
                        />
                      ) : (
                        <FormInputTextField
                          label={field.label}
                          name={`${namePrefix}[${index}].${field.name}`}
                          id={`${namePrefix}-${index}-${field.name}`}
                          value={fieldValue}
                          onChange={onChange}
                        />
                      )}
                    </Col>
                  );
                })}

              <Col md={1}>
                <Button
                  type="button"
                  variant="link"
                  className="bi bi-dash px-0"
                  onClick={() => handleRemove(index)}
                />
              </Col>
            </Row>
          </Form.Group>
        );
      })}
    </Form.Group>
  );
};

export default MultiInputGroup;
