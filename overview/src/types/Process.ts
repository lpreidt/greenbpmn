export type ProcessId = string;

export type Process = {
  id: ProcessId;
  name: string;
  processType: 'management' | 'core' | 'support';
  processDescription: string;
  department: string;
  energySumYear: number;
  childProcess?: ProcessId;
  parent?: ProcessId;
  startKnoten?: [ProcessId];
  endKnoten?: [ProcessId];
} 