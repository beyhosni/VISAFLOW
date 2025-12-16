"use client";

import { useForm } from "react-hook-form";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import api from "@/lib/api";
import { Loader2 } from "lucide-react";
import { toast } from "sonner"; // If we use sonner, or just alert/console for now if not setup
import { useState, useEffect } from "react";

interface Question {
    id: string;
    label: string;
    type: "TEXT" | "DATE" | "SELECT" | "BOOLEAN";
    required: boolean;
    options?: string[];
}

interface QuestionnaireFormProps {
    caseId: string;
    questions: Question[];
    initialAnswers: Record<string, any>;
}

export function QuestionnaireForm({ caseId, questions, initialAnswers }: QuestionnaireFormProps) {
    const queryClient = useQueryClient();
    const [isSaving, setIsSaving] = useState(false);

    const form = useForm({
        defaultValues: initialAnswers || {}
    });

    const mutation = useMutation({
        mutationFn: async (data: any) => {
            return api.post(`/cases/${caseId}/questionnaire/answers`, { answers: data });
        },
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["answers", caseId] });
            // toast("Answers saved");
            alert("Answers saved!");
        },
        onError: () => {
            alert("Failed to save answers");
        }
    });

    function onSubmit(data: any) {
        setIsSaving(true);
        mutation.mutate(data, {
            onSettled: () => setIsSaving(false)
        });
    }

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                {questions.map((q) => (
                    <FormField
                        key={q.id}
                        control={form.control}
                        name={q.id}
                        rules={{ required: q.required ? "This field is required" : false }}
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>{q.label} {q.required && "*"}</FormLabel>
                                <FormControl>
                                    {q.type === "TEXT" && <Input {...field} value={field.value || ""} />}
                                    {q.type === "DATE" && <Input type="date" {...field} value={field.value || ""} />}
                                    {q.type === "SELECT" && (
                                        <Select onValueChange={field.onChange} defaultValue={field.value}>
                                            <SelectTrigger>
                                                <SelectValue placeholder="Select an option" />
                                            </SelectTrigger>
                                            <SelectContent>
                                                {q.options?.map((opt) => (
                                                    <SelectItem key={opt} value={opt}>{opt}</SelectItem>
                                                ))}
                                            </SelectContent>
                                        </Select>
                                    )}
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                ))}

                <Button type="submit" disabled={isSaving}>
                    {isSaving && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                    Save Answers
                </Button>
            </form>
        </Form>
    );
}
